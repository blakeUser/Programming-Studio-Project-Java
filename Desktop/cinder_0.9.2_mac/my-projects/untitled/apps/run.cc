#include <cstdlib>
#include <ctime>
#include <string>
#include <vector>

#include <cinder/app/App.h>
#include <cinder/app/RendererGl.h>
#include "SnakeApp.h"
using cinder::app::App;
using cinder::app::RendererGl;
using std::string;
using std::vector;
using namespace std;
const int kSamples = 8;

void ParseArgs(vector<string>* args) {
    int argc = static_cast<int>(args->size());
    vector<char*> argvs;
    char** argv = argvs.data();
}

void SetUp(App::Settings* settings) {
    vector<string> args = settings->getCommandLineArgs();
    const int width = static_cast<int>(16 * 50);
    const int height = static_cast<int>(16 * 50);
    settings->setWindowSize(width, height);
    settings->setResizable(false);
    settings->setTitle("CS 126 Snake");
    // Make non-deterministic. Really should use C++ random library.
    // Maybe this can be enhanced?
    std::srand(std::time(0));
}

CINDER_APP(SnakeApp,
           RendererGl(RendererGl::Options().msaa(kSamples)), //can be replaced by a simpler version
           SetUp)
