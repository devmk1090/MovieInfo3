package com.bhdev1215.movieinfo3.data.repository

import com.bhdev1215.movieinfo3.data.remote.TMDBApi
import com.bhdev1215.movieinfo3.data.remote.response.MovieDetailResponse
import com.bhdev1215.movieinfo3.model.artist.CastDetail
import com.bhdev1215.movieinfo3.model.artist.CastFilmography
import com.bhdev1215.movieinfo3.model.artist.Credit
import com.bhdev1215.movieinfo3.model.video.Videos
import com.bhdev1215.movieinfo3.util.Resource
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: TMDBApi) {

    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetailResponse> {
        val response = try {
            api.getMovieDetail(movieId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getMovieVideos(movieId: Int): Resource<Videos> {
        val response = try {
            api.getMovieVideos(movieId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getMovieCredit(movieId: Int): Resource<Credit> {
        val response = try {
            api.getMovieCredit(movieId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getCastDetail(personId: Int): Resource<CastDetail> {
        val response = try {
            api.getCastDetail(personId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getCastFilmography(personId: Int): Resource<CastFilmography> {
        val response = try {

            api.getCastFilmography(personId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }
}