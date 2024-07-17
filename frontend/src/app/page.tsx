import Image from "next/image";
import {ModeToggle} from "@/components/mode-toggle";
import {Button} from "@/components/ui/button";

export default function Home() {



    return (
        <>
            <ModeToggle/>
            <Button >Click me</Button>
        </>

    );
}
