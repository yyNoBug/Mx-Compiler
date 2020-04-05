set -e
cd "$(dirname "$0")"
echo "$0"
mkdir -p bin
find ./src -name *.java | javac -d bin -classpath "./ANTLR/antlr-4.8-complete.jar" @/dev/stdin