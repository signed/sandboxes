# Options to execute typescript code

1. tsx: looks more promising and uptodate
2. ts-node: ran into know bugs

going with tsx.

Tsx passes forwards almost all command line arguments to node.
The official [debugging-guide](https://nodejs.org/en/docs/guides/debugging-getting-started) gives more details.

# Execute node with open debug port

```
tsx --inspect
```
If you run a demon process like an express application and you do not immediately want to connect a debugger.
The debug port is open and you can connect later on in case you want to debug a certain api call.

# Pause execution until a debugger connects

```
tsx --inspect-brk
```
For scripts that run to completion without any user input.
The execution of the script will be not start, until a debugger connects.
