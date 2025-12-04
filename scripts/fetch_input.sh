
help () {
   echo "Usage: ./fetch_input.sh -y <year> -d <day> -s <session_cookie>"
   echo ""
   echo "Options:"
    echo "  -y    Year of the Advent of Code (e.g., 2025 defaults to current year if not provided)"
    echo "  -d    Day of the Advent of Code (e.g., 3 required)"
    echo "  -s    Session cookie value (required)"
   echo "  -h    Show this help message"
   exit 1
}

while getopts "y:d:s:h" opt; do
  case $opt in
    y) YEAR="$OPTARG" ;;
    d) DAY="$OPTARG" ;;
    s) SESSION="$OPTARG" ;;
    h) help ;;
    *) help ;;
  esac
done

if [ -z "$DAY" ] || [ -z "$SESSION" ]; then
    echo "Error: Day and session cookie are required."
    help
fi

if [ -z "$YEAR" ]; then
    YEAR=$(date +%Y)
fi
d=$DAY
DAY=$(printf "%02d" $DAY)


curl --cookie "session=$SESSION" https://adventofcode.com/$YEAR/day/$d/input > /Users/chuka.nwobi/Development/advent-of-code/src/year$YEAR/day$DAY/input.txt