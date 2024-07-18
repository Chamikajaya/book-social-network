"use client";

import React, {useState} from 'react';
import {useForm} from 'react-hook-form';
import axios from 'axios';
import {useAuth} from "../../context/auth-context";

interface BookFormData {
    title: string;
    authorName: string;
    isbn: string;
    synopsis: string;
    isShareable: boolean;
}

const AddBook: React.FC = () => {

    const {token} = useAuth();

    const {register, handleSubmit, formState: {errors}} = useForm<BookFormData>();
    const [bookId, setBookId] = useState<number | null>(null);
    const [coverImage, setCoverImage] = useState<File | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const onSubmit = async (data: BookFormData) => {
        setIsLoading(true);
        setError(null);

        try {
            const response = await axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/books`,
                data, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    },
                });

            console.log(response)

            setBookId(response.data);

            if (coverImage) {
                await uploadCoverImage(response.data);
            }

            // Reset form or show success message
            alert('Book added successfully!');
        } catch (error) {
            setError('Failed to add book. Please try again.');
            console.error('Error adding book:', error);
        } finally {
            setIsLoading(false);
        }
    };

    const uploadCoverImage = async (bookId: number) => {
        if (!coverImage) return;

        const formData = new FormData();
        formData.append('file', coverImage);

        try {
            await axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/books/cover/${bookId}`
                , formData, {
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

    return (
        <div className="max-w-md mx-auto mt-10">
            <h1 className="text-2xl font-bold mb-5">Add New Book</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
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

                <button
                    type="submit"
                    disabled={isLoading}
                    className="w-full bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 disabled:bg-blue-300"
                >
                    {isLoading ? 'Adding Book...' : 'Add Book'}
                </button>
            </form>
        </div>
    );
};

export default AddBook;