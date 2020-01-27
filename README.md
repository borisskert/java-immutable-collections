# Immutable collections

This project contains immutable collections that can be used in older Java projects.

## Usage

No maven or gradle configuration! Just copy the needed source files into your project.

## Collections

### ImmutableList

#### Creation

Use on of these factory methods:

```
    List<String> fromSingleItems = ImmutableList.of("A", "B", "C");
    List<String> fromArray = ImmutableList.of(new String[]{"A", "B", "C"});
```

For Streams you can collect the items:

```
    List<String> collectedFromStream = Stream.of("A", "B", "C")
            .collect(ImmutableList.collect());
```
