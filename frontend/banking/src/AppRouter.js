import Reat from "react";
import App from "./App";
import SocialLogin from "./sociallogin/SocialLogin";
import SignUp from "./sociallogin/SignUp";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Account from "./account/Account";
import AccountTransfer from "./account/AccountTransfer";

function AppRouter() {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<App/>}></Route>
                    <Route path="/login" element={<SocialLogin/>}></Route>
                    <Route path="/signup" element={<SignUp/>}></Route>
                    <Route path="/account" element={<Account/>}></Route>
                    <Route path="/account/transfer" element={<AccountTransfer/>}></Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default AppRouter;
