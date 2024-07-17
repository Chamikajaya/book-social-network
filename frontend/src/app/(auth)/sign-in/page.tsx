import SignInForm from "@/components/auth-components/sign-in-form";
import { Suspense } from "react";
import MyLoader from "@/components/loader";


export default function SignInPage() {
    return (
        <div className="flex m-4 items-center justify-center min-h-screen">
            <Suspense fallback={<MyLoader/>}>
                <SignInForm />
            </Suspense>
        </div>
    );
}