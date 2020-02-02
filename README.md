# Immutable collections

This project contains immutable collections that can be used in older Java projects.

## Usage

No maven or gradle configuration! Just copy the needed source files into your project.

## Collections

### ImmutableList

Implements a decorator for Lists which is immutable.

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

#### ImmutableMap

Implements a decorator for Maps which is immutable.

#### Creation

```
    Map<String, String> fromEntries = ImmutableMap.of(
            ImmutableMap.entry("1", "A"),
            ImmutableMap.entry("2", "B"),
            ImmutableMap.entry("3", "C")
    );

    Map<String, String> fromMap = ImmutableList.of(new HashMap<>());
```

For Streams you can collect the items:

```
    Map<String, String> collectedMap = yourStream
            .collect(
                    ImmutableMap.collect(
                            <keyMapperFunction>,
                            <valueMapperFunction>
                    )
            );
```
