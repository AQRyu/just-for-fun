import { MessageList as RCEMessageList } from "react-chat-elements";
const MessageList = ({ selectedWorkspace, messages }) => {
  const formattedMessages = messages.map((msg) => ({
    position: msg.sender === "You" ? "right" : "left",
    type: "text",
    text: msg.content,
    date: new Date(msg.timestamp),
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
