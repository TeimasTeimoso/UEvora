import math
from typing import Dict, Tuple, final
import numpy as np
import pandas as pd
from pandas.core import groupby
from pandas.core.algorithms import value_counts
"""
Returns the number of occurences for each
value in a specified column
"""
def get_values_occurrences(x_data: pd.DataFrame, attribute: str) -> Dict[str, int]:
    return dict(x_data[attribute].value_counts())

"""
Given a dictionary of attribute's value and result frequencies,
it groups them by attribute's value.
"""
def group_by_attribute_value(dictionary: dict) -> Dict[str, float]:
    frequency_by_attribute_value = {}
    
    for entry in dictionary.items():
        key = entry[0][0]

        if key in frequency_by_attribute_value:
            frequency_by_attribute_value[key].append(entry[1])
        else:
            frequency_by_attribute_value[key] = [entry[1]]

    return frequency_by_attribute_value

"""
Given an attribute name and the results it computes 
the frequency for each value
"""
def get_frequency_for_attributes_values(x_data: pd.DataFrame, y_data:pd.DataFrame, attribute: str) -> dict:
    results_column_name = y_data.columns[-1]
    complete_data = x_data.copy()
    complete_data[results_column_name] = y_data 
    occurrence_by_attributes_values = get_values_occurrences(x_data, attribute)
    
    occurrence_grouped_by_values_and_results = dict(complete_data.groupby([attribute, results_column_name])[attribute].count())

    frequency_by_values = {x[0]: x[1]/occurrence_by_attributes_values.get(x[0][0]) for x in occurrence_grouped_by_values_and_results.items()}

    frequency_by_attribute_value = group_by_attribute_value(frequency_by_values)

    return frequency_by_attribute_value

def get_frequency_for_results(occurrence_by_results: dict, total_number_of_occurrences: int) -> dict:

    frequency_by_results = {x[0]: x[1]/total_number_of_occurrences for x in occurrence_by_results.items()}
    
    return frequency_by_results

"""
It computes the entropy for a given attribute
"""
def calculate_entropy(probabilities_of_attribute: list) -> float:
    entropy = 0

    for probability in probabilities_of_attribute:
        entropy += -probability * np.log2(probability)

    return entropy

def calculate_information_gain(x_data: pd.DataFrame, y_data:pd.DataFrame, attribute: str) -> float:
    info_gain = 0

    y_column_name = y_data.columns[0]

    occurrence_by_results = dict(y_data[y_column_name].value_counts())
    occurrence_by_values = dict(x_data[attribute].value_counts())
    total_number_of_occurrences = sum(occurrence_by_results.values())

    results_frequency = get_frequency_for_results(occurrence_by_results, total_number_of_occurrences)
    attributes_entropy = calculate_entropy(results_frequency.values())

    info_gain += attributes_entropy

    attribute_values = list(x_data[attribute].unique())
    attributes_values_frequencies = get_frequency_for_attributes_values(x_data, y_data, attribute)

    for value in attribute_values:
        info_gain += - (occurrence_by_values.get(value)/total_number_of_occurrences) * calculate_entropy(attributes_values_frequencies.get(value))
    
    return info_gain

def calculate_gini(x_data: pd.DataFrame, y_data: pd.DataFrame, attribute: str) -> float:
    final_gini_index = 0

    y_column_name = y_data.columns[0]
    number_of_y_unique_values = len(y_data.iloc[:, 0].unique())
    print(number_of_y_unique_values)

    complete_data = x_data.copy()
    complete_data[y_column_name] = y_data 

    occurrence_by_results = dict(y_data[y_column_name].value_counts())
    occurrence_grouped_by_values_and_results = dict(complete_data.groupby([attribute, y_column_name])[attribute].count())
    entrys = group_by_attribute_value(occurrence_grouped_by_values_and_results)
    total_number_of_occurrences = sum(occurrence_by_results.values())

    attribute_values = list(x_data[attribute].unique())

    for value in attribute_values:
        ocurrences_with_value = sum(entrys.get(value))
        partial_gini_index = (ocurrences_with_value/total_number_of_occurrences * number_of_y_unique_values)

        while len(entrys.get(value)) < number_of_y_unique_values:
            tmp = entrys.get(value)
            tmp.append(0)
            entrys[value] = tmp

        for entry in entrys.get(value):
            partial_gini_index *=  (entry/ocurrences_with_value)
        final_gini_index += partial_gini_index

    return 1-final_gini_index
        

def choose_best_attribute(x_data: pd.DataFrame, y_data: pd.DataFrame, attribute_list: list, measure: str) -> str:
    best_attribute = ('', 0)

    for attribute in attribute_list:
        if measure == 'gini':
            attribute_purity = calculate_gini(x_data, y_data, attribute)
        else:
            attribute_purity = calculate_information_gain(x_data, y_data, attribute)
        if attribute_purity >= best_attribute[1]:
            best_attribute = (attribute, attribute_purity)

    return best_attribute[0]


"""
Checks if the dataset is homogenous
"""
def is_homogeneous(y_data: pd.DataFrame) -> Tuple[bool, str]:
    homogeneous = y_data.nunique() == 1

    if homogeneous[0]:
        # get the first value from data frame
        return (True, y_data.iloc[0,0])

    return (False, '')


"""
return the value with most occurences
"""
def most_of_y(y_data: pd.Series) -> str:
    occurences = y_data.value_counts()
    return (list(occurences.index)[0][0])