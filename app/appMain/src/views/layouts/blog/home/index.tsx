import type {FC, ReactNode} from "react";

const BlogLayout: FC<{ children?: ReactNode }> = ({children}) => {
    return (
        <div>
            {children}
        </div>
    );
}

export default BlogLayout;