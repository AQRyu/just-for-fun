import React, { createContext, useContext, useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";

const StompContext = createContext();

export const StompProvider = ({ children }) => {
  const [stompClient, setStompClient] = useState(null);
  const [isConnected, setIsConnected] = useState(false);
  const [subscriptions, setSubscriptions] = useState({});

  useEffect(() => {
    const token = JSON.parse(localStorage.getItem("user"))?.jwt;
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      onConnect: () => {
        console.log("Stomp Context: Connected");
        setStompClient(client);
        setIsConnected(true);
      },
      onDisconnect: () => {
        console.log("Stomp Context: Disconnected");
        setIsConnected(false);
      },
      onStompError: (frame) => {
        console.error("STOMP error (Stomp Context):", frame.body);
        setIsConnected(false);
      },
      onWebSocketError: (error) => {
        console.error("WebSocket error (Stomp Context):", error);
        setIsConnected(false);
      },
      reconnectDelay: 60000,
      heartbeatIncoming: 0,
      heartbeatOutgoing: 0,
    });

    client.activate();
    setStompClient(client);

    return () => {
      if (client && client.connected) {
        client.deactivate();
      }
    };
  }, []);

  const subscribeToChat = (chatId, onMessageReceivedCallback) => {
    if (!stompClient || !isConnected) {
      console.error(
        "Stomp client not connected, cannot subscribe to chat:",
        chatId
      );
      return;
    }

    const subscribeDestination = `/topic/workspaces/${chatId}`;

    if (subscriptions[chatId]) {
      subscriptions[chatId].unsubscribe();
      setSubscriptions((prevSubs) => {
        const newSubs = { ...prevSubs };
        delete newSubs[chatId];
        return newSubs;
      });
    }

    const subscription = stompClient.subscribe(
      subscribeDestination,
      (message) => {
        debugger;
        const body = JSON.parse(message.body);
        if (onMessageReceivedCallback) {
          onMessageReceivedCallback(body.content, chatId);
        }
      }
    );

    setSubscriptions((prevSubs) => ({ ...prevSubs, [chatId]: subscription }));
    console.log(
      `Stomp Context: Subscribed to chat: ${chatId} on ${subscribeDestination}`
    );
  };

  const unsubscribeFromChat = (chatId) => {
    if (subscriptions[chatId]) {
      subscriptions[chatId].unsubscribe();
      setSubscriptions((prevSubs) => {
        const newSubs = { ...prevSubs };
        delete newSubs[chatId];
        return newSubs;
      });
      console.log(`Stomp Context: Unsubscribed from chat: ${chatId}`);
    }
  };

  const sendMessageToChat = (chatId, messagePayload) => {
    debugger;
    if (!stompClient || !isConnected) {
      console.error(
        "Stomp client not connected, cannot send message to chat:",
        chatId
      );
      return;
    }
    const sendDestination = `/app/workspaces/${chatId}`;
    stompClient.publish({
      destination: sendDestination,
      body: JSON.stringify(messagePayload),
    });
  };

  const contextValue = {
    isConnected,
    subscribeToChat,
    unsubscribeFromChat,
    sendMessageToChat,
  };

  return (
    <StompContext.Provider value={contextValue}>
      {children}
    </StompContext.Provider>
  );
};

export const useStomp = () => useContext(StompContext);
