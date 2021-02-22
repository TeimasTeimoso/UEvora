import sys
from ortools.sat.python import cp_model

# default size
DEFAULT_SIZE = 4
DEFAULT_MODE = 0

# my solution printer for all Solutions
class SolutionPrinter(cp_model.CpSolverSolutionCallback):

    def __init__(self, variables):
        cp_model.CpSolverSolutionCallback.__init__(self)
        self.__variables = variables
        self.__solution_count = 0

    def on_solution_callback(self):
        self.__solution_count += 1

        print("[", end = ' ')
        for v in self.__variables:
            print(self.Value(v), end = ' ')
        print("]")

# my solution printer with limited amount of solutions
class SolutionPrinterWithLimit(cp_model.CpSolverSolutionCallback):

    def __init__(self, variables, limit):
        cp_model.CpSolverSolutionCallback.__init__(self)
        self.__variables = variables
        self.__solution_count = 0
        self.__solutions_limit = limit

    def on_solution_callback(self):
        self.__solution_count += 1

        for v in self.__variables:
            print(self.Value(v), end = ' ')
        print()

        if self.__solution_count >= self.__solutions_limit: # Stop at the limit
            self.StopSearch()

class CostasArraySolver:

    model = cp_model.CpModel()

    def __init__(self, array_size):
        self.dimensions = array_size  # creates the array where index represents the row
        self.array = [self.model.NewIntVar(1, self.dimensions, f'r{i}') for i in range(self.dimensions)] # create the r_i variables from array according its domain
        self.diff_triangle = []       # difference triangle

    # creating the difference triangle variables according its domain
    def create_diff_triangle(self):
        for distance in range(1, self.dimensions):
            self.diff_triangle.append([self.model.NewIntVar(1-self.dimensions, self.dimensions-1, f'd{distance}i{index}') for index in range(self.dimensions-distance)])

    # auxiliary constraint function to map the diff triangle variables to the according difference
    def calculate_diff_triangle(self):
        for distance in range(1, self.dimensions):
            for index in range(self.dimensions-distance):
                self.model.Add(self.array[index+distance] - self.array[index] == self.diff_triangle[distance-1][index])

    # function to apply the constraints to the model 
    def add_constrainsts_to_model(self):
        self.model.AddAllDifferent(self.array)  # 1st constraint => alldifferent(array)
        self.add_redundant_all_different_constraint()
        self.calculate_diff_triangle()          # 2nd constriant => Vi+d−Vdfor alli=1,. . .,n-1 

        for row in range(self.dimensions-1):                    # 3rd constraint 
            self.model.AddAllDifferent(self.diff_triangle[row]) # alldifferent(ri) for eachr=1,. . .,n-1

    # this is a redudant constraint in order to increase propagation and create holes in domain
    # If the value of the difference triangle position is the subtraction of 2 elements of the array
    # And they cannot be the same, then the difference cannot be 0
    def add_redundant_all_different_constraint(self):
        for distance in range(1, self.dimensions):
            for index in range(self.dimensions-distance):
                self.model.Add(self.diff_triangle[distance-1][index] != 0)

    def create_solver(self):
       return cp_model.CpSolver()

    def solve(self, mode):
        self.create_diff_triangle()
        self.add_constrainsts_to_model()
        solver = self.create_solver()

        if mode == 0:   # to find all solutions
            solution_printer = SolutionPrinter(self.array)
        else:           # to find one solution
            solution_printer = SolutionPrinterWithLimit(self.array, 1)

        solver.SearchForAllSolutions(self.model, solution_printer)
            

if __name__ == "__main__":

    mode_options = ["0", "1"]

    if len(sys.argv) >= 3:
        if sys.argv[2] not in mode_options:
            print("usage: n m(0 || 1)")
            sys.exit(1)
        mode = int(sys.argv[2])
    else:
        mode = DEFAULT_MODE

    size = int(sys.argv[1]) if (len(sys.argv) >= 1) else DEFAULT_SIZE 
    costas_array = CostasArraySolver(size)
    costas_array.solve(mode)


