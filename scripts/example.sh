#!/bin/bash

DIR=$(dirname $0)
CMD="$DIR/project-markdown-renderer.sh"
CLASS="de.topobyte.Test"

exec "$CMD" "$CLASS" "$@"
