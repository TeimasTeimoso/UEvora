def compress(input: bytes, symbol_table: dict) -> str:
    compressed_data = ''
    for char in input:
        compressed_data += symbol_table.get(char)

    return compressed_data