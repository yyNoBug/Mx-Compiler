set -e
cd "$(dirname "$0")"
cat > test.txt
java -cp ./ANTLR/antlr-4.8-complete.jar:./bin Main