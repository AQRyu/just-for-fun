import * as StompJs from "@stomp/stompjs";
import { useEffect, useState } from "react";
import ChatUI from "./ChatUI";

const client = new StompJs.Client({
  brokerURL: "ws://localhost:8080/ws",
  connectHeaders: {
    // ... any authentication headers, if applicable ...
  },
  reconnectDelay: 5000,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000,
});

function Chat() {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");

  useEffect(() => {
    client.activate();

    client.onConnect = () => {
      // Ensure connection before subscribing
      client.subscribe("/topic/newMessage", (message) => {
        console.log(message);
        setMessages((prevMessages) => [
          ...prevMessages,
          JSON.parse(message.body),
        ]);
      });
    };

    client.onStompError = (frame) => {
      console.error("Broker reported error: ", frame.headers["message"]);
      console.error("Additional details: ", frame.body);
    };

    return () => {
      if (client.active) {
        client.deactivate();
      }
    };
  }, [messages]);

  const sendMessage = () => {
    if (newMessage.trim() !== "") {
      // Prevent sending empty messages
      client.publish({
        destination: "/app/sendMessage",
        body: JSON.stringify({ sender: "user", content: newMessage }),
      });
      setNewMessage(""); // Clear input after sending
    }
  };

  return (
    <ChatUI
      messages={messages}
      sendMessage={sendMessage}
      newMessage={newMessage} // Pass newMessage state
      setNewMessage={setNewMessage} // Pass setNewMessage function
    />
  );
}

export default Chat;
