# Hashify

Generate password hashes using various algorithms

## Usage

1. Download the [script](https://github.com/cm090/hashify/releases/latest/download/hashify.jar) from the repository.
2. Make sure [JDK21](https://learn.microsoft.com/en-us/java/openjdk/download#openjdk-21) is installed on your system.
3. Run the script
   ```bash
   java -jar hashify.jar <inputFile> <outputFile>
   ```
   where `<inputFile>` is the path to the input file containing one password per line and `<outputFile>` is the path to
   the CSV output file where the hashes will be saved.

## Example

Contents of `input.txt`:

```
mypassword
123456
password123
```

Note that `output.csv` should not exist before running the script.

```bash
java -jar hashify.jar path/to/input.txt path/to/output.csv
```

## Add an Algorithm

1. Navigate to `src/main/java/hashing/algorithms`.
2. Create a new class that implements the `HashingAlgorithm` interface.
3. Override the `hash` method to implement the hashing logic. This returns an `Entry` object containing the algorithm
   name and the hashed value.

   **Note:** The `AlgorithmManager` class has a helper function, `bytesToHex`, that converts a byte array to a hex
   string.
4. Add the new class to the list of `ALGORITHMS` in `src/main/java/hashing/AlgorithmManager.java`.
5. Rebuild the jar file
    ```bash
    mvn clean compile assembly:single
    ```
   
   Located at `target/hashify-<version>-jar-with-dependencies.jar`.
