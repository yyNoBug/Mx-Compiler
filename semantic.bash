set -e
cd "$(dirname "$0")"
cat > test.txt
java -cp "./ulib/java/antlr-4.8-complete.jar:./bin Main"