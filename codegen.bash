set -e
cd "$(dirname "$0")"
cat > "test.txt"
java -cp "/ulib/java/antlr-4.8-complete.jar:./bin:antlr/antlr-4.8-complete.jar" Main codegen