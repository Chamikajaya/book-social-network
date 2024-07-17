import {Button} from "@/components/ui/button";
import Link from "next/link";

interface BackBtnProps{
    backBtnLabel:string;
    backBtnLink:string;
}

export default function BackBtn({backBtnLink, backBtnLabel}:BackBtnProps) {
    return (
        <Button
            asChild={true}
            size={"sm"}
            variant={"link"}
            className={"font-medium w-full"}
        >
            <Link href={backBtnLink}>{backBtnLabel}</Link>
        </Button>
    )
}