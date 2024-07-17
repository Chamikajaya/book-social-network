import {z} from "zod";

const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

export const signUpSchema = z
    .object({
        email: z
            .string()
            .email({
                message: "You must provide a valid email",
            })
            .trim(),
        firstName: z.string().min(3),
        lastName: z.string().min(3),
        password: z
            .string()
            .min(8, "Password must be at least 8 characters long")
            .regex(passwordRegex, {
                message:
                    "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character",
            }),
        confirmPassword: z.string(),
    })
    .refine(
        (values) => {
            return values.password === values.confirmPassword;
        },
        {
            message: "Passwords must match!",
            path: ["confirmPassword"],
        }
    );

export type SignUpSchema = z.infer<typeof signUpSchema>;


export const signInSchema = z
    .object({
        email: z
            .string()
            .email({
                message: "You must provide a valid email",
            })
            .trim(),
        password: z
            .string()

    })


export type SignInSchema = z.infer<typeof signInSchema>;


