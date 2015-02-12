#!/bin/bash
#
# Copyright 2015 Red Hat, Inc. and/or its affiliates
# and other contributors as indicated by the @author tags.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


set -xe

BUS_BUILD_LOG="${HOME}/bus.build.log"

cd "${HOME}"

git clone --depth=50 https://github.com/hawkular/hawkular-bus.git hawkular/hawkular-bus
cd hawkular/hawkular-bus
mvn clean install -DskipTests > "${BUS_BUILD_LOG}" 2>&1 || (cat "${BUS_BUILD_LOG}" && exit 1)