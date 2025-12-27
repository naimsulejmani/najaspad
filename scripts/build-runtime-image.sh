#!/usr/bin/env bash
set -euo pipefail

# Resolve repo root (scripts/..)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "$REPO_ROOT"

TAG="najaspad:runtime"

echo "Building runtime image: $TAG"
docker build -f Dockerfile.runtime -t "$TAG" .

echo

echo "Runtime image size:"
docker image inspect "$TAG" --format '{{.RepoTags}} {{.Size}}'

