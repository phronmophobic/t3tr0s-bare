# T3TR0S-bare

This is a bare version of single player
[T3TR0S](http://github.com/imalooney/t3tr0s), because we miss the accessibility
and simplicity of its code before features become a priority.

An example of running tetris with sci+graalvm.

![tetris](/tetris.gif?raw=true)

## Play with the REPL

1. Follow the [membrane example](https://github.com/phronmophobic/mobiletest#membrane-example) instructions.
2. Connect to the repl
3. Run the namespaces in the following order: `board.clj`, `rules.clj`, `paint.clj`, `core.clj`
4. From the core namespace, run `(init)` to start the game.
