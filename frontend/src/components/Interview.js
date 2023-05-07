import React, {useState} from 'react';
import axios from 'axios';


function Interview() {
    const [message, setMessage] = useState('');
    const [userName, setUserName] = useState('');
    const [responseMessage, setResponseMessage] = useState('Hello, what\'s your name?');
    const [isLoading, setIsLoading] = useState(false);

    const sendMessage = async () => {
        try {
            //if no user was sent then first question is to send his name
            let currentUserName = userName;
            if (!userName) {
                setUserName(message);
                currentUserName = message
            }

            if (!message) {
                return;
            }
            setIsLoading(true);
            debugger;
            const requestData = {
                userName: currentUserName,
                message: message,
            };
            const response = await axios.post(`http://${window.location.hostname}:8080/interview`, requestData);
            setResponseMessage(response.data);
            setMessage('');
        } catch (error) {
            console.log(error);
            setResponseMessage('Error sending message.');
        } finally {
            setIsLoading(false);
        }
    };

    const handleMessageChange = (e) => {
        setMessage(e.target.value);
    };

    return (

        <div>
            <h2 className="interview-question">{responseMessage}</h2>
            <div className="interview-container">
                <textarea
                    className="interview-input"
                    name="userMessage"
                    id="userMessage"
                    value={message}
                    onChange={handleMessageChange}
                    placeholder="Type your message here..."
                ></textarea>
                <button className="interview-send-button"
                        onClick={sendMessage}
                        disabled={!message || isLoading}
                >
                    Send
                </button>
            </div>
        </div>
    );
}

export default Interview;