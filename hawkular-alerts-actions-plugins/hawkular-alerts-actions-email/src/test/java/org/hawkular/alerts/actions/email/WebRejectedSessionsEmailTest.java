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
package org.hawkular.alerts.actions.email;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;

import org.hawkular.alerts.actions.api.ActionMessage;
import org.hawkular.alerts.actions.tests.TestActionMessage;
import org.hawkular.alerts.actions.tests.WebRejectedSessionsData;
import org.hawkular.alerts.api.model.action.Action;
import org.hawkular.alerts.api.model.condition.Alert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jay Shaughnessy
 * @author Lucas Ponce
 */
public class WebRejectedSessionsEmailTest extends CommonTest {

    private static EmailPlugin plugin;
    private static Map<String, String> properties;

    @BeforeClass
    public static void prepareMessages() {
        plugin = new EmailPlugin();

        properties= new HashMap<>();
        properties.put("to", "admin@hawkular.org");
        properties.put("cc", "bigboss@hawkular.org");
        properties.put("cc.acknowledged", "acknowledged@hawkular.org");
        properties.put("cc.resolved", "resolved@hawkular.org");
        properties.put("template.hawkular.url", "http://www.hawkular.org");
    }

    @Test
    public void writeOpenEmailTest() throws Exception {
        Alert openAlert = WebRejectedSessionsData.getOpenAlert();

        Action openAction = new Action(openAlert.getTriggerId(), "email", "email-to-test", openAlert);

        ActionMessage openMessage = new TestActionMessage(openAction, properties);

        Message email = plugin.createMimeMessage(openMessage);
        assertNotNull(email);
        writeEmailFile(email, this.getClass().getSimpleName() + "-1-open.eml");
    }

    @Test
    public void writeAcknowledgeEmailTest() throws Exception {
        Alert openAlert = WebRejectedSessionsData.getOpenAlert();
        Alert ackAlert = WebRejectedSessionsData.ackAlert(openAlert);

        Action ackAction = new Action(ackAlert.getTriggerId(), "email", "email-to-test", ackAlert);

        ActionMessage ackMessage = new TestActionMessage(ackAction, properties);

        Message email = plugin.createMimeMessage(ackMessage);
        assertNotNull(email);
        writeEmailFile(email, this.getClass().getSimpleName() + "-2-ack.eml");
    }

    @Test
    public void writeResolvedEmailTest() throws Exception {
        Alert openAlert = WebRejectedSessionsData.getOpenAlert();
        Alert ackAlert = WebRejectedSessionsData.ackAlert(openAlert);
        Alert resolvedAlert = WebRejectedSessionsData.resolveAlert(ackAlert);

        Action resolvedAction = new Action(resolvedAlert.getTriggerId(), "email", "email-to-test", resolvedAlert);

        ActionMessage resolvedMessage = new TestActionMessage(resolvedAction, properties);

        Message email = plugin.createMimeMessage(resolvedMessage);
        assertNotNull(email);
        writeEmailFile(email, this.getClass().getSimpleName() + "-3-resolved.eml");
    }

}
