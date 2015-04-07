# TicTacToe game
A CLI version of simple TicTacToe game.

How to use from project folder:

To compile source files:
```bash
$ javac -d compiled -cp "src" src/init/Main.java
```

To create jar:
```bash
$ jar -cfm TicTacToe.jar manifest.txt -C compiled .```

To run:
```bash
$ java -cp compiled init.Main
```

To run .jar package:
```bash
$ java -jar TicTacToe.jar
```

* If you wish to use save/load functionality, there must be `input-files/save.txt` in the same directory.
