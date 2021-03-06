/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.alerts.api.model.condition;

import java.io.Serializable;
import java.util.Map;

import org.hawkular.alerts.api.json.JacksonDeserializer;
import org.hawkular.alerts.api.model.condition.Condition.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * An evaluation state of a specific condition.
 *
 * @author Jay Shaughnessy
 * @author Lucas Ponce
 */
@JsonDeserialize(using = JacksonDeserializer.ConditionEvalDeserializer.class)
public abstract class ConditionEval implements Serializable {

    // result of the condition evaluation
    @JsonIgnore
    protected boolean match;

    // time of condition evaluation (i.e. creation time)
    @JsonInclude
    protected long evalTimestamp;

    // time stamped on the data used in the eval
    @JsonInclude
    protected long dataTimestamp;

    @JsonInclude
    protected Condition.Type type;

    @JsonInclude(Include.NON_EMPTY)
    protected Map<String, String> context;

    public ConditionEval() {
        // for json assembly
    }

    public ConditionEval(Type type, boolean match, long dataTimestamp, Map<String, String> context) {
        this.type = type;
        this.match = match;
        this.dataTimestamp = dataTimestamp;
        this.evalTimestamp = System.currentTimeMillis();
        this.context = context;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public long getEvalTimestamp() {
        return evalTimestamp;
    }

    public void setEvalTimestamp(long evalTimestamp) {
        this.evalTimestamp = evalTimestamp;
    }

    public long getDataTimestamp() {
        return dataTimestamp;
    }

    public void setDataTimestamp(long dataTimestamp) {
        this.dataTimestamp = dataTimestamp;
    }

    public Condition.Type getType() {
        return type;
    }

    public void setType(Condition.Type type) {
        this.type = type;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    @JsonIgnore
    public abstract String getTriggerId();

    @JsonIgnore
    public abstract int getConditionSetSize();

    @JsonIgnore
    public abstract int getConditionSetIndex();

    @JsonIgnore
    public abstract String getLog();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionEval that = (ConditionEval) o;

        if (evalTimestamp != that.evalTimestamp) return false;
        if (dataTimestamp != that.dataTimestamp) return false;
        if (type != that.type) return false;
        return !(context != null ? !context.equals(that.context) : that.context != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (evalTimestamp ^ (evalTimestamp >>> 32));
        result = 31 * result + (int) (dataTimestamp ^ (dataTimestamp >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (context != null ? context.hashCode() : 0);
        return result;
    }
}
