import { MessageList as RCEMessageList } from "react-chat-elements";
const MessageList = ({ selectedWorkspace, messages }) => {
  const formattedMessages = messages.map((msg) => ({
    position: msg.sender === "You" ? "right" : "left", // Adjust logic as needed
    type: "text",
    text: msg.content,
    date: new Date(msg.timestamp), // Assuming your backend sends a timestamp
    // avatar: ... you might need to fetch avatar based on sender
    sender: msg.sender,
  }));

  if (!selectedWorkspace) {
    return (
      <div style={{ padding: 20, textAlign: "center" }}>
        Select a chat to see messages
      </div>
    );
  }

  return (
    <RCEMessageList
      className="message-list"
      lockBottom={true}
      toBottomHeight={"100%"}
      dataSource={formattedMessages}
    />
  );
};

export default MessageList;
