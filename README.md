# java-tictactoe
TicTacToe game written in Java, can be run with CLI.

How to use from project folder:

To compile source files:
`$ javac -d compiled -cp "src" src/init/Main.java`

To create jar:
$ jar -cfm TicTacToe.jar manifest.txt -C compiled .

To run:
`$ java -cp compiled init.Main`

To run .jar package (*):
`$ java -jar TicTacToe.jar`

* If you wish to use save/load functionality, there must be in the same directory with `.jar` file:
Folder `input-files` with `save.txt` inside. File can be empty.
