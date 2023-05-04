import React from 'react';

const Integrations = () => {
    return (
        <div>
            <h1>Integrations</h1>
            <p>
                This page is for managing third-party integrations with the app. Below are some of the integrations we support:
            </p>
            <ul>
                <li>
                    <strong>Slack</strong> - Receive notifications about new leads and suspects in a Slack channel.
                </li>
                <li>
                    <strong>Email</strong> - Receive email alerts about new leads and suspects.
                </li>
                <li>
                    <strong>Webhooks</strong> - Send data about new leads and suspects to a custom URL.
                </li>
            </ul>
        </div>
    );
};

export default Integrations;
