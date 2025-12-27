#!/usr/bin/env fish
set -l repo_root (dirname (realpath (status -f)))
set repo_root (dirname $repo_root)

cd $repo_root

set -l tag "najaspad:runtime"

echo "Building runtime image: $tag"
docker build -f Dockerfile.runtime -t $tag .

echo "\nRuntime image size:"
docker image inspect $tag --format '{{.RepoTags}} {{.Size}}'


