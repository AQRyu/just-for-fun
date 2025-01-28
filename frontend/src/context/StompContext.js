import React, {
  createContext,
  useContext,
  useState,
  useEffect,
  useCallback,
} from "react";
import { Client } from "@stomp/stompjs";

const StompContext = createContext();

export const StompProvider = ({ children }) => {
  const [stompClient, setStompClient] = useState(null);
  const [isConnected, setIsConnected] = useState(false);
  const [activeSubscription, setActiveSubscription] = useState(null);

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

    return () => {
      if (client && client.connected) {
        client.deactivate();
      }
    };
  }, []);

  const subscribeToChat = useCallback(
    (workspaceId, onMessageReceivedCallback) => {
      if (!stompClient || !isConnected) {
        console.error(
          "Stomp client not connected, cannot subscribe to chat:",
          workspaceId
        );
        return;
      }

      if (activeSubscription) {
        activeSubscription.unsubscribe();
      }

      const subscribeDestination = `/topic/workspaces/${workspaceId}`;
      const subscription = stompClient.subscribe(
        subscribeDestination,
        (data) => {
          try {
            const body = JSON.parse(data.body);
            if (onMessageReceivedCallback) {
              onMessageReceivedCallback(body, workspaceId);
            }
          } catch (error) {
            console.error("Error handling message:", error, data);
          }
        }
      );

      setActiveSubscription(subscription);

      console.log(
        `Stomp Context: Subscribed to chat: ${workspaceId} on ${subscribeDestination}`
      );
    },
    [activeSubscription, isConnected, stompClient]
  );

  const unsubscribeFromChat = useCallback(() => {
    if (activeSubscription) {
      activeSubscription.unsubscribe();
      setActiveSubscription(null);
      console.log(`Stomp Context: Unsubscribed from chat`);
    }
  }, [activeSubscription]);

  const sendMessageToChat = (chatId, messagePayload) => {
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
