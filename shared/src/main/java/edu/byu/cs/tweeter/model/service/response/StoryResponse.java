package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Story;

import java.util.List;

public class StoryResponse extends PagedResponse {

  private List<Story> stories;

  public StoryResponse(List<Story> stories, boolean hasMorePages) {
    super(true, "success", hasMorePages);
    this.stories = stories;
  }

  public StoryResponse(String s) {
    super(false, s, false);
  }

  public List<Story> getStories() {
    return stories;
  }

  public void setStories(List<Story> stories) {
    this.stories = stories;
  }
}
