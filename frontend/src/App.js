import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import AuthRoute from "./components/AuthRoute";
import Navbar from "./components/Navbar";
import { AuthProvider } from "./context/AuthContext";
import ChatPage from "./pages/ChatPage";
import CreateWorkspacePage from "./pages/CreateWorkspacePage";
import WorkspaceManagementPage from "./pages/WorkspaceManagementPage";
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
            <Route path="/chat" element={<ChatPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/workspace/create" element={<CreateWorkspacePage />} />
            <Route
              path="/workspace/:workspaceId/manage"
              element={<WorkspaceManagementPage />}
            />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
