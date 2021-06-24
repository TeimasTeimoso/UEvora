import numpy as np
import pandas as pd
from pandas.core import groupby

dataset = pd.read_csv('weather.nominal.csv')


def get_frequency_by_value(dataset: pd.Series, attribute: str, results:str) -> dict:
    total_number_of_elements = dataset[attribute].count()
    
    occurences_by_values = dict(dataset.groupby([attribute, results])[attribute].count())

    frequency_by_values = {x[0]: x[1]/total_number_of_elements for x in occurences_by_values.items()}

    return frequency_by_values

print(get_frequency_by_value(dataset, 'outlook', 'play'))

def calculate_entropy(frequency_list: dict) -> float:
    entropy = 0

    for probability in frequency_list.values():
        print(-probability * np.log2(probability))
        entropy += -probability * np.log2(probability)

    return entropy

fl = get_frequency_by_value(dataset, 'outlook', 'play')
print(calculate_entropy(fl))
