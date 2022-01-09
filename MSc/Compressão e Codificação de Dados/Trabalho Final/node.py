class Node:
    def __init__(self, symbol='', left=None, rigth=None, probability=0) -> None:
        self._left_child: Node = left
        self._rigth_child: Node = rigth
        self._symbol: str = symbol
        self._probability: float = probability

    def set_left_child(self, node: 'Node') -> None:
        self._left_child = node

    def set_rigth_child(self, node: 'Node') -> None:
        self._rigth_child = node

    def get_left_child(self) -> 'Node':
        return self._left_child

    def get_rigth_child(self) -> 'Node':
        return self._rigth_child

    def get_symbol(self) -> str:
        return self._symbol

    def get_probability(self) -> float:
        return self._probability
        