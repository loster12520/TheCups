import type {ReactNode} from "react";
import BlogHome from "@/views/pages/blog/home";

const blogRoutes: {
    path: string;
    element: ReactNode;
}[] = [
    {
        path: "/",
        element: <BlogHome/>
    }
]

export {blogRoutes}