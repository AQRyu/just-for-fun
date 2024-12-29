import React from "react";
import "react-chat-elements/dist/main.css";
import Chat from "./components/Chat";

function App() {
  return (
    <div className="App">
      <Chat /> {/* Render the ChatUI component */}
    </div>
  );
}

export default App;
