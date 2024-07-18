"use client";

import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { Button } from "@/components/ui/button";
import {useRouter, useParams } from 'next/navigation';
import {useAuth} from "../../../../../context/auth-context";
import toast from "react-hot-toast";

interface BookFormData {
    id?: number;
    title: string;
    authorName: string;
    isbn: string;
    synopsis: string;
    isShareable: boolean;
}

const EditBook: React.FC = () => {
    const router = useRouter();
    const { bookId } = useParams();
    console.log(useParams());
    const { token } = useAuth();
    const { register, handleSubmit, formState: { errors }, reset } = useForm<BookFormData>();
    const [coverImage, setCoverImage] = useState<File | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchBookDetails = async () => {
            if (!bookId) return;

            try {
                const response = await axios.get(`${process.env.NEXT_PUBLIC_API_BASE_URL}/books/${bookId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                reset(response.data);
            } catch (error) {
                console.error('Error fetching book details:', error);
                setError('Failed to fetch book details. Please try again.');
            }
        };

        fetchBookDetails();
    }, [bookId, token, reset]);

    const onSubmit = async (data: BookFormData) => {
        if (!bookId) return;

        setIsLoading(true);
        setError(null);

        try {
            await axios.put(`${process.env.NEXT_PUBLIC_API_BASE_URL}/books/${bookId}`,
                data, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    },
                });

            if (coverImage) {
                await uploadCoverImage(Number(bookId));
            }

           toast.success('Book updated successfully');
            router.push('/my-books'); // Redirect to books list page
        } catch (error) {
            setError('Failed to update book. Please try again.');
            toast.error('Failed to update book. Please try again.');
            console.error('Error updating book:', error);
        } finally {
            setIsLoading(false);
        }
    };

    const uploadCoverImage = async (bookId: number) => {
        if (!coverImage) return;

        const formData = new FormData();
        formData.append('file', coverImage);

        try {
            await axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/books/cover/${bookId}`,
                formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        Authorization: `Bearer ${token}`,
                    },
                });
        } catch (error) {
            console.error('Error uploading cover image:', error);
            setError('Failed to upload cover image. Please try again.');
        }
    };

    const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            setCoverImage(event.target.files[0]);
        }
    };

    if (!bookId) {
        return <div>Loading...</div>;
    }

    return (
        <div className="max-w-md mx-auto mt-10">
            <h1 className="text-2xl font-bold mb-5">Edit Book</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                {/* Form fields remain the same as in the previous version */}
                <div>
                    <label htmlFor="title" className="block mb-1">Title</label>
                    <input
                        id="title"
                        type="text"
                        {...register('title', {required: 'Title is required'})}
                        className="w-full px-3 py-2 border rounded"
                    />
                    {errors.title && <p className="text-red-500">{errors.title.message}</p>}
                </div>

                <div>
                    <label htmlFor="authorName" className="block mb-1">Author Name</label>
                    <input
                        id="authorName"
                        type="text"
                        {...register('authorName', {required: 'Author name is required'})}
                        className="w-full px-3 py-2 border rounded"
                    />
                    {errors.authorName && <p className="text-red-500">{errors.authorName.message}</p>}
                </div>

                <div>
                    <label htmlFor="isbn" className="block mb-1">ISBN</label>
                    <input
                        id="isbn"
                        type="text"
                        {...register('isbn', {required: 'ISBN is required'})}
                        className="w-full px-3 py-2 border rounded"
                    />
                    {errors.isbn && <p className="text-red-500">{errors.isbn.message}</p>}
                </div>

                <div>
                    <label htmlFor="synopsis" className="block mb-1">Synopsis</label>
                    <textarea
                        id="synopsis"
                        {...register('synopsis', {required: 'Synopsis is required'})}
                        className="w-full px-3 py-2 border rounded"
                    />
                    {errors.synopsis && <p className="text-red-500">{errors.synopsis.message}</p>}
                </div>

                <div>
                    <label htmlFor="isShareable" className="flex items-center">
                        <input
                            id="isShareable"
                            type="checkbox"
                            {...register('isShareable')}
                            className="mr-2"
                        />
                        Is Shareable
                    </label>
                </div>

                <div>
                    <label htmlFor="coverImage" className="block mb-1">Cover Image</label>
                    <input
                        id="coverImage"
                        type="file"
                        onChange={handleImageChange}
                        accept="image/*"
                        className="w-full px-3 py-2 border rounded"
                    />
                </div>

                {error && <p className="text-red-500">{error}</p>}

                <Button
                    type="submit"
                    disabled={isLoading}
                    className="w-full mt-4"
                >
                    {isLoading ? 'Updating Book...' : 'Update Book'}
                </Button>
            </form>
        </div>
    );
};

export default EditBook;