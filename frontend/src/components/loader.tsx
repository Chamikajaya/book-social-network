import {Loader2} from "lucide-react";

export default function MyLoader() {

    return (
        <div className={"flex justify-center items-center my-20"}>
            <Loader2 className={"animate-spin"} size={80}/>
        </div>
    )

}