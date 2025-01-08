import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import AuthRoute from "./components/AuthRoute";
import Navbar from "./components/Navbar";
import { AuthProvider } from "./context/AuthContext";
import ChatPage from "./pages/ChatPage";
import CreateGroupForm from "./pages/CreateGroupForm";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import ProfilePage from "./pages/ProfilePage";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route element={<AuthRoute />}>
            {" "}
            <Route path="/chat" element={<ChatPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/create-group" element={<CreateGroupForm />} />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
