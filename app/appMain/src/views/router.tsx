import {HashRouter, Route, Routes} from "react-router-dom";
import type {ReactNode} from "react";

/**
 * 路由列表
 *
 * TODO:根据实际需求增添其他功能，例如自动检测是否登录、IP封禁等
 */
const routes: {
    path: string;
    element: ReactNode;
}[] = [
    {
        path: "/test",
        element: <div>Test Page</div>
    }
]

/**
 * 基础路由设置
 * @constructor
 */
const AppRouter = () => {
    return <HashRouter>
        <Routes>
            {routes.map((item, index) => {
                return <Route key={index} path={item.path} element={item.element}/>
            })}
        </Routes>
    </HashRouter>
}

export {AppRouter}