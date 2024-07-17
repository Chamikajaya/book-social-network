"use client"

import {zodResolver} from "@hookform/resolvers/zod"
import {useForm} from "react-hook-form"
import {z} from "zod"

import {Button} from "@/components/ui/button"
import {
    Form,
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form"
import {
    InputOTP,
    InputOTPGroup,
    InputOTPSlot,
} from "@/components/ui/input-otp"
import CardWrapper from "@/components/auth-components/card-wrapper";
import toast from "react-hot-toast";
import {useState} from "react";
import axios from "axios";
import FormError from "@/components/form-error";
import {useRouter} from "next/navigation";


const FormSchema = z.object({
    token: z.string().min(6, {
        message: "Your activation code must be 6 characters.",
    }),
})

export function InputOTPForm() {

    const [isSubmitting, setIsSubmitting] = useState(false);
    const [formErrMsg, setFormErrMsg] = useState("");
    const router = useRouter();


    const form = useForm<z.infer<typeof FormSchema>>({
        resolver: zodResolver(FormSchema),
        defaultValues: {
            token: "",
        },
    })

    async function onSubmit(formData: z.infer<typeof FormSchema>) {
        console.log(formData);
        try {
            setIsSubmitting(true);
            setFormErrMsg("");

            const response = await axios.post(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/auth/verify-email`,
                formData,
                {
                    withCredentials: true,
                }
            );

            if (response.status === 200) {
                router.push("/sign-in");
            }
        } catch (error) {
            console.error('Token validation failed:', error);
            if (error.response.status === 409) {
                setFormErrMsg("Invalid activation code.");
            } else if (error.response.status === 500) {
                setFormErrMsg("Current token is expired or invalid. A new token has been sent to your email.");
            }


        } finally {
            setIsSubmitting(false);

        }
    }

    console.log("formErrMsg", formErrMsg);
    return (
        <CardWrapper
            title="Activate Your Account 😊"
            backBtnLabel="Don't have an account ? "
            backBtnLink="/sign-up"
            formTopic={"Verify 🔒"}
        >
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className="w-full flex flex-col items-center space-y-6">
                    <FormField
                        control={form.control}
                        name="token"
                        render={({field}) => (
                            <FormItem className="w-full flex flex-col items-center">
                                <FormLabel>Activation Code</FormLabel>
                                <FormControl className="flex justify-center">
                                    <InputOTP maxLength={6} {...field}>
                                        <InputOTPGroup>
                                            <InputOTPSlot index={0}/>
                                            <InputOTPSlot index={1}/>
                                            <InputOTPSlot index={2}/>
                                            <InputOTPSlot index={3}/>
                                            <InputOTPSlot index={4}/>
                                            <InputOTPSlot index={5}/>
                                        </InputOTPGroup>
                                    </InputOTP>
                                </FormControl>
                                <FormDescription>
                                    Please enter the code sent to your email.
                                </FormDescription>
                                <FormMessage/>
                            </FormItem>
                        )}
                    />
                    <FormError errMessage={formErrMsg}/>
                    <Button type="submit" disabled={isSubmitting}>Submit</Button>
                </form>
            </Form>
        </CardWrapper>
    )
}
