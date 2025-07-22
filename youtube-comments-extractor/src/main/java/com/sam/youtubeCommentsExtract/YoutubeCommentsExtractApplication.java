package com.sam.youtubeCommentsExtract;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class YoutubeCommentsExtractApplication {

    // Google Developer Account - API KEY
    private static final String API_KEY = "*************************";
    private static final String APPLICATION_NAME = "youtube-comment-fetcher";

    public static void main(String[] args) throws Exception {

        /* Initialize YouTube service
            Initializes the YouTube API client using Google's HTTP client and JSON parser.
            Uses JacksonFactory to parse JSON responses.
            No OAuth is used here — you're using an API key (good for public data like comments).
        */
        YouTube youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), // This line creates a secure HTTP transport for making API requests.
                JacksonFactory.getDefaultInstance(), // This tells the API client to use Jackson (a JSON processor) for parsing and generating JSON data.
                null)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // This is the ID of the YouTube video whose comments you want to fetch.
        String videoId = "wxO706hHzb8";

        // Request top-level comments
        YouTube.CommentThreads.List request = youtube.commentThreads()
                .list("snippet")
                .setVideoId(videoId)
                .setTextFormat("plainText") // Request plain text (not HTML)
                .setMaxResults(50L) // Limit to 5 top-level comments
                .setKey(API_KEY); // Authenticate with your API key

        // Executes the API call and parses the JSON response into Java objects.
        CommentThreadListResponse response = request.execute();
        List<CommentThread> comments = response.getItems();

        // Print author and comment
        for (CommentThread ct : comments) {
            var topSnippet = ct.getSnippet().getTopLevelComment().getSnippet();
            var topCommentId = ct.getSnippet().getTopLevelComment().getId();

            // Print top-level comment
            System.out.println(topSnippet.getAuthorDisplayName() + ": " + topSnippet.getTextDisplay());

            // Check for replies
            if (ct.getSnippet().getTotalReplyCount() > 0) {
                // Fetch replies using commentId
                YouTube.Comments.List replyRequest = youtube.comments()
                        .list("snippet")
                        .setParentId(topCommentId)
                        .setTextFormat("plainText")
                        .setMaxResults(5L)
                        .setKey(API_KEY);

                var replyResponse = replyRequest.execute();
                var replies = replyResponse.getItems();

                // Print each reply
                for (var reply : replies) {
                    var replySnippet = reply.getSnippet();
                    System.out.println("  ↳ " + replySnippet.getAuthorDisplayName() + ": " + replySnippet.getTextDisplay());
                }
            }
            System.out.println("-----------------------------------------------------------------------------");
        }

    }
}
