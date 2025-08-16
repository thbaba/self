#!/bin/bash

ollama serve &
sleep 5
ollama pull gemma3:4b &
pull_pid=$!
wait $pull_pid
sleep 5