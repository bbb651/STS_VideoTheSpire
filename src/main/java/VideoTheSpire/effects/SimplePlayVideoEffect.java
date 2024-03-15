package VideoTheSpire.effects;

import VideoTheSpire.Log;
import VideoTheSpire.SettingsPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.logging.FileHandler;

public class SimplePlayVideoEffect extends AbstractGameEffect {

    private VideoPlayer videoPlayer;


    private static final String COMMENT = "The video format needs to be webm !!!";

    public SimplePlayVideoEffect(String videoPath) {
        this.duration = this.startingDuration = 2f;
        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        if (videoPlayer == null) {
            Log.logger.error("createVideoPlayer error");
            clearVideo();
            return;
        }

        videoPlayer.setOnCompletionListener(e -> clearVideo());
        new Thread(() -> {
            try {
                videoPlayer.play(Gdx.files.internal(videoPath));
            } catch (Exception e) {
                Log.logger.error("decode video error", e);
                e.printStackTrace();
                clearVideo();
            }
        }).start();
    }

    @Override
    public void update() {
        videoPlayer.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        Texture texture = videoPlayer.getTexture();
        if (texture != null) {
            float width = texture.getWidth() * Settings.scale;
            float height = texture.getHeight() * Settings.scale;
            float x = (Settings.WIDTH - width) / 2f;
            float y = (Settings.HEIGHT - height) / 2f;
            sb.setColor(Color.WHITE);
            sb.draw(texture, x, y, width, height);
        }
    }

    @Override
    public void dispose() {

    }

    public void clearVideo() {
        isDone = true;
        if (videoPlayer != null) {
            videoPlayer.dispose();
            videoPlayer = null;
        }
    }
}
