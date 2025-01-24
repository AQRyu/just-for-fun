import { Client } from "@stomp/stompjs";
import { createContext, useEffect, useState } from "react";

const StompContext = createContext();
export const StompProvider = ({ children }) => {
  const [stompClient, setStompClient] = useState(null);
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    client.onConnect = () => {
      setIsConnected(true);
      console.log("connected");
    };

    client.onStompError = (frame) => {
      setIsConnected(false);
      console.log("STOMP error:", frame);
    };

    client.onWebSocketClose = () => {
      setIsConnected(false);
      console.warn("WebSocket connection closed");
    };

    client.activate();
    setStompClient(client);

    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  });
};
