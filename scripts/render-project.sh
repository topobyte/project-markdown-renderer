#!/bin/bash

DIR=$(dirname $0)
CMD="$DIR/project-markdown-renderer.sh"
CLASS="de.topobyte.markdownprojects.RenderProject"

exec "$CMD" "$CLASS" "$@"
