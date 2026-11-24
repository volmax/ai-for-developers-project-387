# Changelog

## [0.1.17](https://github.com/volmax/ai-for-developers-project-387/compare/v0.1.16...v0.1.17) (2026-11-24)


### Features

* **ci:** add weekly code audit workflow with schedule and dispatch ([#1](https://github.com/volmax/ai-for-developers-project-387/issues/1)) ([#25](https://github.com/volmax/ai-for-developers-project-387/issues/25)) ([db04bde](https://github.com/volmax/ai-for-developers-project-387/commit/db04bde6738120c50ac87447c0c37b6f5749f031))


### Bug Fixes

* **ci:** add OPENCODE_PERMISSION and GH_TOKEN to weekly audit workflow ([#27](https://github.com/volmax/ai-for-developers-project-387/issues/27)) ([10b7b11](https://github.com/volmax/ai-for-developers-project-387/commit/10b7b114cb0e219cfd6fae8929568cc877f8de55))

## [0.1.16](https://github.com/volmax/ai-for-developers-project-387/compare/v0.1.15...v0.1.16) (2026-11-24)


### Features

* **admin:** show booking topic in upcoming meetings list ([#18](https://github.com/volmax/ai-for-developers-project-387/issues/18)) ([b1a77b4](https://github.com/volmax/ai-for-developers-project-387/commit/b1a77b49fb36b404fea0b6a9da5986d4b01a3c5d))
* **booking:** add optional meeting topic to API ([#16](https://github.com/volmax/ai-for-developers-project-387/issues/16)) ([c37d392](https://github.com/volmax/ai-for-developers-project-387/commit/c37d3924d8152d6a8bbb211a1a771e8e3b01d302))
* **booking:** add optional topic field to booking form ([#17](https://github.com/volmax/ai-for-developers-project-387/issues/17)) ([fe58aa0](https://github.com/volmax/ai-for-developers-project-387/commit/fe58aa0342903cc029ff3e8110f000b224d76c33))
* **ci:** add automated issue triage workflow ([bd1a6b5](https://github.com/volmax/ai-for-developers-project-387/commit/bd1a6b5f3cdd7f6464866c982b72d44cdf181b3a))
* **ci:** add automated issue triage workflow ([#0](https://github.com/volmax/ai-for-developers-project-387/issues/0)) ([927bd04](https://github.com/volmax/ai-for-developers-project-387/commit/927bd04b7f94ce2012e239a4fbf7014e4846f388))
* **ci:** add run limit workflow for PR agent invocations ([#21](https://github.com/volmax/ai-for-developers-project-387/issues/21)) ([bb6ca68](https://github.com/volmax/ai-for-developers-project-387/commit/bb6ca682a57f1749b8de3c81aecdef564b4c209a))
* **docs:** add pull request template ([2d360cf](https://github.com/volmax/ai-for-developers-project-387/commit/2d360cff5afe67a63b04052e9570011eab094aec))
* **docs:** add pull request template with checks, risk, and rollback sections ([8a2175a](https://github.com/volmax/ai-for-developers-project-387/commit/8a2175a8d63148b2bcf11d9baf4d43481458dd15))
* **docs:** add REVIEW.md criteria and opencode-review workflow ([7d3bb90](https://github.com/volmax/ai-for-developers-project-387/commit/7d3bb90ebdcda81a7367748bb21a4cbdcab25ae0))
* **docs:** add REVIEW.md criteria and opencode-review workflow ([0fd2506](https://github.com/volmax/ai-for-developers-project-387/commit/0fd2506d175e03bb92ab0dcbffcdb2b433bc0fa2))
* **workflows:** switch to glm-5.3-flash ([cefdab3](https://github.com/volmax/ai-for-developers-project-387/commit/cefdab34577fd6618fc88b898922c11f1110b8d6))
* **workflows:** switch to Kimi K2.7 Code model ([2f6796e](https://github.com/volmax/ai-for-developers-project-387/commit/2f6796ec7787bde9a463026129f5649e2983ff4b))
* **workflows:** switch to Kimi K2.7 Code model ([23026b7](https://github.com/volmax/ai-for-developers-project-387/commit/23026b79a1fb7376e99ef2ad9befcb4ef7e73101))


### Bug Fixes

* **booking:** validate topic length after normalization ([#16](https://github.com/volmax/ai-for-developers-project-387/issues/16)) ([adf1098](https://github.com/volmax/ai-for-developers-project-387/commit/adf1098ba13aa935d2fa45980ccd7ffa5803a67c))
* **ci:** remove invalid max-parallel from concurrency block ([9959102](https://github.com/volmax/ai-for-developers-project-387/commit/9959102bca2ce70476c16d41c650bea7fa7d82fd))
* **ci:** remove invalid max-parallel from concurrency block ([#8](https://github.com/volmax/ai-for-developers-project-387/issues/8)) ([d6c8fae](https://github.com/volmax/ai-for-developers-project-387/commit/d6c8faee49a0152fde69fcee5c53b6aa1dfd341e))
* **triage:** increase timeout and simplify prompt ([#10](https://github.com/volmax/ai-for-developers-project-387/issues/10)) ([1d2f4c0](https://github.com/volmax/ai-for-developers-project-387/commit/1d2f4c05147db14453f4a405deeb34603f8f4112))
* **triage:** increase timeout and simplify prompt ([#10](https://github.com/volmax/ai-for-developers-project-387/issues/10)) ([d9a1068](https://github.com/volmax/ai-for-developers-project-387/commit/d9a106827003b99197274d744d401f648976a91b))
* update Render deployment URL ([#54](https://github.com/volmax/ai-for-developers-project-387/issues/54)) ([eea5dee](https://github.com/volmax/ai-for-developers-project-387/commit/eea5deed0eaf49856b6f282c0c1b3c69179abfa6))
* update Render deployment URL ([#54](https://github.com/volmax/ai-for-developers-project-387/issues/54)) ([547e26a](https://github.com/volmax/ai-for-developers-project-387/commit/547e26a567f81bb7c4398af40907847a4b51e815))
* **workflow:** pass GH_TOKEN to agent and fix external_directory permission format ([#14](https://github.com/volmax/ai-for-developers-project-387/issues/14)) ([e7f620d](https://github.com/volmax/ai-for-developers-project-387/commit/e7f620df9a5f1f2317a28b721eaff16ec132cafa))
* **workflows:** add PR_NUMBER env to publish notice step ([2d6c956](https://github.com/volmax/ai-for-developers-project-387/commit/2d6c9569609905d0f027df59ce8db2afb12575b6))
* **workflows:** exclude current run from count and add --argjson for jq ([5333d50](https://github.com/volmax/ai-for-developers-project-387/commit/5333d5091545bcf39d676da085200176e08d3d53))
* **workflows:** fix all review findings in opencode-limit and opencode-review ([#22](https://github.com/volmax/ai-for-developers-project-387/issues/22)) ([0a26105](https://github.com/volmax/ai-for-developers-project-387/commit/0a261051ee8db475230b360b3d2242f519b049ac))
* **workflows:** fix all review findings in opencode-limit and opencode-review ([#22](https://github.com/volmax/ai-for-developers-project-387/issues/22)) ([cdc31b2](https://github.com/volmax/ai-for-developers-project-387/commit/cdc31b23589b50f0e6d6df6f325ba729c803db64))
* **workflows:** pipe gh output to jq, exclude current run via --argjson ([7e0c848](https://github.com/volmax/ai-for-developers-project-387/commit/7e0c84803985bdd1efc4d4efe0727c12f2d5bf7d))
* **workflows:** remove Check limit step (exits 1 breaks job; review job skips via needs) ([8988944](https://github.com/volmax/ai-for-developers-project-387/commit/8988944f51b33b37d6c67758bebd223670c74f2a))
* **workflows:** use workflowName instead of workflow for gh run list --json ([7bafc67](https://github.com/volmax/ai-for-developers-project-387/commit/7bafc67f2917a27af69a7e6f35f3bb5344bf1d11))

## [0.1.15](https://github.com/volmax/ai-for-developers-project-387/compare/v0.1.14...v0.1.15) (2026-11-23)


### Features

* add initial project setup ([592786d](https://github.com/volmax/ai-for-developers-project-387/commit/592786dddcd39fb74816b37902ce4a46b432d0dd))
* add initial project setup ([127495c](https://github.com/volmax/ai-for-developers-project-387/commit/127495c5e7e37206f7e4755e373126b462952998))
* **backend:** rename project from 386 to 387 ([9316af1](https://github.com/volmax/ai-for-developers-project-387/commit/9316af161bdd85c64214e85950663d0c6a7800be))
* **backend:** rename project from 386 to 387 (#NN) ([efc4e28](https://github.com/volmax/ai-for-developers-project-387/commit/efc4e288f611bd2170a43834659facf99264a656))
