package ecez.vndbapp.model;

/**
 * Created by Teng on 10/10/2016.
 */

    public class NovelData {

        protected int votecount;
        protected int length;
        protected int rank;
        protected Double rating, popularity;
        protected String title, released, id, image;

        public NovelData () {}

        public NovelData(String title, Double rating, int length, String image, int rank, String id, String released, Double popularity, int votecount) {
            this.title = title;
            this.rating = rating;
            this.length = length;
            this.image = image;
            this.rank = rank;
            this.id = id;
            this.released = released;
            this.votecount = votecount;
            this.popularity = popularity;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getImage() {
            return image;
        }

        public String getLength() {
            switch (length) {
                case 1:
                    return "Very short (< 2 hours)";
                case 2:
                    return "Short (2 - 10 hours)";
                case 3:
                    return "Medium (10 - 30 hours)";
                case 4:
                    return "Long (30 - 50 hours)";
                default:
                    return "Very Long (> 50 hours)";
            }
        }

        public String getId() {
            return id;
        }

        public String getRating() {
            if (rating > 9){
                return rating.toString() + " (Excellent)";
            } else if (rating > 8) {
                return rating.toString() + " (Very Good)";
            } else if (rating > 7) {
                return rating.toString() + " (Good)";
            } else if (rating > 6) {
                return rating.toString() + " (Decent)";
            } else if (rating == 0 && votecount == 0) {
                return ("No Rating");
            } else {
                return rating.toString() + " (Poor)";
            }
        }

        public String getPopularity() {
            return Double.toString(popularity) + "% Popularity";
        }


        public String getTitleWithDate() {
            if (released.equals("tba"))
                return getTitle() + " (TBA)";
            return getTitle() + " (" + released.substring(0,released.indexOf("-")) + ")";
        }

        public String getTitle() {
            if (title.equals(""))
                return "No Title";
            return title;
        }

        public String getVoteCount() {
            if (votecount == 0)
                return "No Votes";
            return Integer.toString(votecount) + " Votes";
        }

        public String getTitleWithDateAndRank () {
            return "#" + Integer.toString(rank) + " - " + this.getTitleWithDate();
        }
    }

