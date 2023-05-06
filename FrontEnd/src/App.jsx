import { Routes, BrowserRouter, Route } from 'react-router-dom';
import PrivateRoutes from "./components/common/PrivateRoutes";
import MainPage from './components/catalogs/MainPage';
import UsersPage from './components/catalogs/Users';
import CatalogStudents from './components/catalogs/News';
import Post from './components/catalogs/Post';
import Footer from './components/common/Footer';
import LoginPage from "./components/catalogs/LoginPage";
import SignupPage from "./components/catalogs/SignupPage";
import NavBar from "./components/common/NavBar";
import Account from './components/catalogs/Account';

export default function App() {
      const links = [
          { path: 'main', label: "Main", userGroup: "AUTH" },
          { path: 'news', label: "News", userGroup: "AUTH" },
          { path: 'users', label: "Users", userGroup: "ADMIN" },
          { path: 'account', label: "Account", userGroup: "AUTH" },
        ];
      return(
            <>
                <BrowserRouter>
                    <div className='body_app'>
                      <NavBar links={links}></NavBar>
                      <div className="d-flex flex-column text-white bg-info bg-gradient fw-bold ">
                          <Routes>
                              <Route element={<LoginPage />} path="/login" />
                              <Route element={<SignupPage />} path="/signup" />
                              <Route element={<PrivateRoutes userGroup="AUTH" />}>
                                  <Route element={<CatalogStudents />} path="/news" />
                                  <Route element={<MainPage />} path="/main" exact />
                                  <Route element={<MainPage />} path="/main" exact />
                                  <Route element={<Account />} path="/account" exact />
                                  <Route element={<Post />} path="/Post" />
                              </Route>
                              <Route element={<PrivateRoutes userGroup="ADMIN" />}>
                                  <Route element={<UsersPage />} path="/users" />
                              </Route>
                          </Routes>
                      </div>
                      <Footer className="border-top">
                          Footer
                      </Footer  >
                  </div>
              </BrowserRouter>
          </>
      );

}